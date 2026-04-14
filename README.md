# 📚 MobileLibrary

**Современное Android-приложение для поиска, просмотра и управления любимыми книгами.**

[![Build](https://github.com/ivanioffe/MobileLibrary/actions/workflows/Build.yaml/badge.svg)](https://github.com/ivanioffe/MobileLibrary/actions/workflows/Build.yaml)
[![Version](https://img.shields.io/github/v/release/ivanioffe/MobileLibrary?include_prereleases&label=Version&color=blue)](https://github.com/ivanioffe/MobileLibrary/releases/latest)

## ✨ Основные возможности

- 🔐 **Авторизация** — вход через Google Sign-In (Google Identity + Credential Manager)
- 🏠 **Главная страница** — персонализированные рекомендации и популярные книги
- 🔍 **Поиск** — умный поиск по названию, автору и жанру
- 📖 **Детали книги** — полная информация, обложка, описание и возможность добавить в избранное
- ❤️ **Избранное** — список сохранённых книг с удобным управлением
- 🔄 **Оффлайн-режим** — кэширование данных через Room и DataStore

## 🛠 Технологический стек

- **Kotlin**
- **Jetpack Compose, Material 3**
- **Coil**
- **Navigation Compose**
- **Clean Architecture**
- **MVI**
- **Hilt**
- **Coroutines, Flow**
- **Retrofit, OkHttp**
- **Room**
- **DataStore**
- **Kotlinx Serialization**
- **SplashScreen API**
- **Google Identity / Play Services Auth**
- **JUnit4 / JUnit5**, **MockK**, **Turbine**, **Robolectric**, **Roborazzi**
- **Custom Gradle plugins**

## 🏗 Структура проекта

Проект построен по **многомодульной архитектуре** с разделением на `core` и `feature`-модули.

```bash
MobileLibrary/
├── app/                          # Точка входа приложения, основная навигация и сборка экрана верхнего уровня
├── build-logic/                  # Custom Gradle plugins (convention plugins)
├── core/
│   ├── common/                   # Общие утилиты и базовые сущности
│   ├── data/                     # Переиспользуемые реализации репозиториев и источники данных
│   ├── database/                 # Room DB
│   ├── datastore-auth/           # Данные, связанные с авторизацией
│   ├── datastore-user/           # Данные пользователя
│   ├── designsystem/             # Дизайн-система (темы, компоненты)
│   ├── domain/                   # Бизнес-логика и переиспользуемые Use Case и репозитории
│   ├── model/                    # Общие domain модели
│   ├── network/                  # API-клиент, DTO модели и сетевые настройки
│   ├── presentation/             # Общие презентационные компоненты (MVI)
│   ├── screenshot-testing/       # Инфраструктура для screenshot tests
│   └── ui/                       # Переиспользуемые UI-компоненты и UI утилиты
├── feature/
│   ├── book-details/             # Экран детальной информации о книге
│   ├── favourite-books/          # Список избранного
│   ├── home/                     # Главный экран
│   ├── search/                   # Поиск книг
│   └── sign-in/                  # Экран авторизации
├── .github/workflows/            # CI/CD
└── gradle/libs.versions.toml     # Единый каталог версий
```

## 🚀 Запуск проекта

1.  **Клонируйте репозиторий:**
    ```bash
    git clone https://github.com/ivanioffe/MobileLibrary.git
    ```
    или
    ```bash
    git clone git@github.com:ivanioffe/MobileLibrary.git
    ```
2. **Синхронизируйте Gradle:**
   Дождитесь загрузки всех зависимостей.

3. При необходимости заполните `secrets.properties` на основе `secrets.defaults.properties`.

4. Запустите модуль `app` на устройстве или эмуляторе.

> Требования:
> - Android SDK 26+
> - Kotlin 2.2.21+

---

Автор: [IvanIoffe](https://github.com/IvanIoffe)

> *Если вам понравилось приложение — ставьте ⭐ и делитесь с друзьями!*
