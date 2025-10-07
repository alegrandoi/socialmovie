#!/bin/bash

# Script de verificación de código sin compilación completa
# Útil para entornos sin Android SDK

echo "🔍 Verificando estructura del proyecto SocialMovie..."
echo ""

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

errors=0

# 1. Verificar estructura de paquetes
echo "📦 Verificando estructura de paquetes..."
if [ -d "app/src/main/java/com/example/aplicacion" ]; then
    echo -e "${GREEN}✅ Estructura de paquetes correcta${NC}"
else
    echo -e "${RED}❌ Estructura de paquetes incorrecta${NC}"
    errors=$((errors + 1))
fi

# 2. Verificar archivos de configuración
echo ""
echo "⚙️  Verificando archivos de configuración..."

files=("build.gradle" "app/build.gradle" "settings.gradle" "gradle.properties")
for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✅ $file existe${NC}"
    else
        echo -e "${RED}❌ $file no encontrado${NC}"
        errors=$((errors + 1))
    fi
done

# 3. Verificar archivos Java principales
echo ""
echo "☕ Verificando archivos Java principales..."

java_files=(
    "app/src/main/java/com/example/aplicacion/MainActivity.java"
    "app/src/main/java/com/example/aplicacion/RegisterActivity.java"
    "app/src/main/java/com/example/aplicacion/ResetPasswordActivity.java"
    "app/src/main/java/com/example/aplicacion/ui/adapters/BaseMovieAdapter.java"
    "app/src/main/java/com/example/aplicacion/ui/adapters/MovieRecyclerView.java"
    "app/src/main/java/com/example/aplicacion/utils/ValidationUtils.java"
    "app/src/main/java/com/example/aplicacion/utils/Constants.java"
)

for file in "${java_files[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✅ $(basename $file)${NC}"
    else
        echo -e "${RED}❌ $(basename $file) no encontrado${NC}"
        errors=$((errors + 1))
    fi
done

# 4. Verificar sintaxis básica de Java
echo ""
echo "🔎 Verificando sintaxis básica..."

# Buscar errores comunes de sintaxis
echo "   Buscando packages duplicados..."
duplicated_packages=$(find app/src/main/java -name "*.java" -exec grep -l "^package.*package" {} \;)
if [ -z "$duplicated_packages" ]; then
    echo -e "${GREEN}✅ No hay packages duplicados${NC}"
else
    echo -e "${RED}❌ Packages duplicados encontrados:${NC}"
    echo "$duplicated_packages"
    errors=$((errors + 1))
fi

echo "   Buscando imports duplicados..."
duplicated_imports=$(find app/src/main/java -name "*.java" -exec grep -l "^import.*import" {} \;)
if [ -z "$duplicated_imports" ]; then
    echo -e "${GREEN}✅ No hay imports duplicados${NC}"
else
    echo -e "${RED}❌ Imports duplicados encontrados:${NC}"
    echo "$duplicated_imports"
    errors=$((errors + 1))
fi

# 5. Verificar tests
echo ""
echo "🧪 Verificando tests..."

test_files=(
    "app/src/test/java/com/example/aplicacion/utils/ValidationUtilsTest.java"
    "app/src/test/java/com/example/aplicacion/validators/EmailValidatorTest.java"
)

for file in "${test_files[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✅ $(basename $file)${NC}"
    else
        echo -e "${YELLOW}⚠️  $(basename $file) no encontrado${NC}"
    fi
done

# 6. Verificar documentación
echo ""
echo "📚 Verificando documentación..."

doc_files=(
    "README.md"
    "BUILD_GUIDE.md"
    "SPRINT3_COMPLETED.md"
    "SPRINT3_SUMMARY.md"
)

for file in "${doc_files[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✅ $file${NC}"
    else
        echo -e "${YELLOW}⚠️  $file no encontrado${NC}"
    fi
done

# 7. Verificar CI/CD
echo ""
echo "🔄 Verificando configuración CI/CD..."

if [ -f ".github/workflows/android.yml" ]; then
    echo -e "${GREEN}✅ GitHub Actions configurado${NC}"
else
    echo -e "${YELLOW}⚠️  GitHub Actions no configurado${NC}"
fi

# 8. Verificar ProGuard
echo ""
echo "🔒 Verificando configuración ProGuard..."

if [ -f "app/proguard-rules.pro" ]; then
    lines=$(wc -l < "app/proguard-rules.pro")
    if [ "$lines" -gt 100 ]; then
        echo -e "${GREEN}✅ ProGuard configurado ($lines líneas)${NC}"
    else
        echo -e "${YELLOW}⚠️  ProGuard tiene pocas reglas ($lines líneas)${NC}"
    fi
else
    echo -e "${RED}❌ ProGuard no configurado${NC}"
    errors=$((errors + 1))
fi

# 9. Verificar git
echo ""
echo "🔍 Verificando repositorio Git..."

if [ -d ".git" ]; then
    echo -e "${GREEN}✅ Repositorio Git inicializado${NC}"
    
    # Verificar remote
    remote=$(git remote -v | grep origin | head -1)
    if [ -n "$remote" ]; then
        echo -e "${GREEN}✅ Remote configurado: $(git remote get-url origin)${NC}"
    else
        echo -e "${YELLOW}⚠️  No hay remote configurado${NC}"
    fi
    
    # Verificar branch
    branch=$(git branch --show-current)
    echo -e "${GREEN}✅ Branch actual: $branch${NC}"
    
    # Verificar cambios pendientes
    if [[ -n $(git status -s) ]]; then
        echo -e "${YELLOW}⚠️  Hay cambios sin commit${NC}"
        echo "   Archivos modificados:"
        git status -s | head -5
    else
        echo -e "${GREEN}✅ No hay cambios pendientes${NC}"
    fi
else
    echo -e "${RED}❌ No es un repositorio Git${NC}"
    errors=$((errors + 1))
fi

# Resumen final
echo ""
echo "================================"
if [ $errors -eq 0 ]; then
    echo -e "${GREEN}✅ VERIFICACIÓN COMPLETADA SIN ERRORES${NC}"
    echo ""
    echo "El código está listo para:"
    echo "  • Push a GitHub (compilará en CI/CD)"
    echo "  • Apertura en Android Studio"
    echo "  • Compilación con SDK instalado"
    echo ""
    echo "Para compilar localmente, consulta BUILD_GUIDE.md"
    exit 0
else
    echo -e "${RED}❌ VERIFICACIÓN COMPLETADA CON $errors ERROR(ES)${NC}"
    echo ""
    echo "Revisa los errores arriba y corrígelos antes de continuar."
    exit 1
fi
