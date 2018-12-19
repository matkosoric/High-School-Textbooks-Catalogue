# High-School Textbooks Catalogue

This is a REST compliant web service for accessing and editing a catalogue of Croatian high-school textbooks.
It will be accessible on `/textbooks-catalogue-service` path and port `8080`.


### Dataset

Textbooks data set was created from the official list of textbooks in Croatian high schools used in the period from 2014 to 2018.
Sample data is in /src/main/java/resources/textbooks_subset.csv.

[Original textbooks xml list](https://mzo.hr/hr/popis-udzbenika-pripadajucih-dopunskih-nastavnih-sredstava-za-skolsku-godinu-20142015?cat=209)


### Tools

[Spring Boot 2.0.1](https://spring.io/projects/spring-boot)  
[PostgreSQL 10.6](https://www.postgresql.org/)  
[DBeaver 5.2](https://dbeaver.io/)  
[Postman 6.5.3](https://www.getpostman.com/)


### Security

This service uses in-memory authentication.
All endpoints are exposed for GET requests without authentication.
Users with the USER role can create new entries in the database.
Users with the ADMIN role can additionally edit and delete database entries.
There are two users with the following credentials:
  
username: user  
password: user  
  
username: admin  
password: admin  

### Screenshots

![1. request - Matko Soric](https://raw.githubusercontent.com/matkosoric/High-School-Textbooks-Catalogue/master/src/main/resources/screenshots/1-textbook-service-matko-soric.png?raw=true "1. request- Matko Soric")
  
![2. request - Matko Soric](https://raw.githubusercontent.com/matkosoric/High-School-Textbooks-Catalogue/master/src/main/resources/screenshots/2-textbook-service-matko-soric.png?raw=true "2. request- Matko Soric")
  
![3. request - Matko Soric](https://raw.githubusercontent.com/matkosoric/High-School-Textbooks-Catalogue/master/src/main/resources/screenshots/3-textbook-service-matko-soric.png?raw=true "3. request- Matko Soric")
  
![4. request - Matko Soric](https://raw.githubusercontent.com/matkosoric/High-School-Textbooks-Catalogue/master/src/main/resources/screenshots/4-textbook-service-matko-soric.png?raw=true "4. request- Matko Soric")
  
![5. request - Matko Soric](https://raw.githubusercontent.com/matkosoric/High-School-Textbooks-Catalogue/master/src/main/resources/screenshots/5-textbook-service-matko-soric.png?raw=true "5. request- Matko Soric")
  
![6. request - Matko Soric](https://raw.githubusercontent.com/matkosoric/High-School-Textbooks-Catalogue/master/src/main/resources/screenshots/6-textbook-service-matko-soric.png?raw=true "6. request- Matko Soric")
  
![7. request - Matko Soric](https://raw.githubusercontent.com/matkosoric/High-School-Textbooks-Catalogue/master/src/main/resources/screenshots/7-textbook-service-matko-soric.png?raw=true "7. request- Matko Soric")
  