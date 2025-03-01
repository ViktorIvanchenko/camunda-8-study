# Тестова ERP

Даний модуль містить в собі тестову ERP систему яка використовується для повноцінної реалізації
сценаріїв навчальних BPMN бізнес-процесів.

В рамках тестової ERP реалізовано наступні одиниці функціональності:

- тестова організаційна структура 
  - підрозділи [OrgUnit.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2FOrgUnit.java)
  - посади [Position.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2FPosition.java)
  - співробітники [Employee.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2FEmployee.java)
- тестове обладнання та матеріальні засоби які є в компанії
  - обладнання [Equipment.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2Fequipment%2FEquipment.java)
  - витратні матеріали [Material.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2Fmaterail%2FMaterial.java)
  - інформація про наявність витратних матеріалів в розрізі партій матеріалів [BatchOfMaterial.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2Fmaterail%2FBatchOfMaterial.java)
  - норми витрат [ConsumptionRate.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2Fmaterail%2FConsumptionRate.java)
  - норми забезпечення матеріалами [ConsumptionRate.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2Fmaterail%2FConsumptionRate.java)
  - інформація про технічне обслуговування і ремонт обладнання [Maintenance.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2Fequipment%2FMaintenance.java)
- відомості про відрядження [Trip.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2Ftrip%2FTrip.java)
- відомості про відпустки [Vacation.java](erp%2Fsrc%2Fmain%2Fjava%2Fua%2Fcom%2Fintegrity%2Fbpm%2Fcamunda%2Fstudy%2Fdomain%2Fvacation%2FVacation.java)

Для роботи з даними одиницями функціональності ERP система забезпечує наступні API:

1. REST API для роботи з усією функціональністю системи (OpenAPI definition <SERVER_URL>/openapi/erp?format=JSON)
2. SOAP API для роботи з організаційною структурою (WSDL file location - <SERVER_URL>/erp/org-structure?wsdl)
3. SOAP API для роботи з матеріалами (WSDL file location - <SERVER_URL>/erp/materials?wsdl)
4. SOAP API для роботи з обладнанням та технічним обслуговуванням (WSDL file location - <SERVER_URL>/erp/equipments?wsdl)

## Локальний запуск

```bash
docker-compose up -d --build
```

В рамках docker-compose буде виконано збирання тестової ERP та її розгортання на сервері застосунків Wildfly.

## Відомості про тестову організаційну структуру

![test-org-structure.jpg](assets%2Ftest-org-structure.jpg)

## Зв'язки між сутностями тестової ERP

![entity-classes.png](assets%2Fentity-classes.png)