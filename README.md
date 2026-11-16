# Отчет по лабораторной работе 4

Автоматизированные тесты на сайте [old.vyatsu.ru](https://old.vyatsu.ru)

## Стек технологий

* **Java 17**
* **Maven**
* **Selenium WebDriver 4.52.0**
* **JUnit 4.13.2**
* **AssertJ 3.27.7**

---

## Результаты выполнения тестовых сценариев

### 1. Базовая проверка открытия сайта и заголовка (`openSiteTest`, `titleTest`)
| Открытие главной страницы | Проверка заголовка |
| :---: | :---: |
| ![Открытие страницы](screenshots/1_open.png) | ![Заголовок верный](screenshots/2_title_ok.png) |

---

### 2. Демонстрация падающего теста (`titleFailDemo`)
![Автоматический скриншот при падении](screenshots/FAILED_titleFailDemo.png)

---

### 3. Сценарий 1: Навигация по меню в раздел «Образовательные стандарты»

| Раскрытие меню через Actions | Переход на страницу стандартов |
| :---: | :---: |
| ![Открытое меню](screenshots/menu_opened.png) | ![Страница образовательных стандартов](screenshots/standards_page_opened.png) |

---

### 4. Сценарий 2: Поиск по сайту

| Ввод поискового запроса | Выдача результатов поиска |
| :---: | :---: |
| ![Ввод запроса](screenshots/search_input_filled.png) | ![Результаты поиска](screenshots/search_results.png) |

---

### 5. Сценарий 3: Подписка на новости в разделе «Студенту»
#### Скриншоты шагов подписки

| Ввод корректного e-mail | Ответ сайта (корректный e-mail) |
| :---: | :---: |
| ![Ввод корректного email](screenshots/valid_email_entered.png) | ![Ответ на корректный email](screenshots/valid_email_response.png) |

| Ввод некорректного e-mail | Ответ сайта (некорректный e-mail) |
| :---: | :---: |
| ![Ввод некорректного email](screenshots/invalid_email_entered.png) | ![Ответ на некорректный email](screenshots/invalid_email_response.png) |

---

## Разбор возникших ошибок и их исправление

### 1. Перекрытие кнопки поиска (`ElementClickInterceptedException`)

В сценарии 2 при попытке выполнить физический клик по кнопке отправки формы, 
так как текстовое поле `<input type="text" name="query">` визуально накладывается поверх координат кнопки «Найти».
`.click()` был заменен на отправку формы нажатием клавиши Enter.

![Скриншот ошибки ElementClickInterceptedException](screenshots/error_click_intercepted.png)
