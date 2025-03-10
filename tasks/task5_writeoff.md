# Процес списання застарілої техніки

## Мета

Автоматизувати процес заміни застарілої техніки для того, щоб співробітники завжди працювали на якісному обладнанні.

## Опис процесу

ERP система щодня публікує в MQ чергу повідомлень про застарілу техніку, строк експлуатації якої вже минув і балансова вартість якої 0. Повідомлення містить дані про застарілу техніку.

1. Процес починається з вилучення повідомлення з черги.

2. Виконується перевірка чи вже існують процеси по списанню вказаної техніки, якщо такі процеси знайдені - цей процес завершується.

3. Визначається співробітник, за яким закріплена вказана техніка. Якщо техніка не закріплена за жодним з співробітників - переходимо до кроку 8.

4. Якщо техніка закріплена за певним співробітником, то проводиться автоматичний підбір аналогічної техніки із наявних запасів.
    - якщо в запасах немає підходящої техніки, то створюється завдання для адміністратора на закупівлю нової техніки
    - після виконання завдання адміністратором, крок 4 виконується знову.

5. На співробітника накладається завдання підготувати вказану техніку до здачі та підтвердити отримання нової техніки.

6. Паралельно створюється завдання для адміністратора, де вказано, яку техніку він повинен прийняти, а яку видалити взамін.

7. Після виконання кроків співробітником і адміністратором процес переходить до етапу списання застарілої техніки.

8. Застаріла техніка переміщується до списаної.
