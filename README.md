# AcademicMisinformation

ISS Project - Amice Team


## Client: WEB

### Technology: React + Typescript
People: Nuti, Andreea, Iulia, Ed


## Backend:

### Technology: Spring
People: Calin, Alex, Andreea, Iulia


## Database:

### Technology: PostgreSQL
People: Alex


### Notes:
- assume **Java SpringBoot** as backend technology
- **Spring Data JPA** – facilitates data access and operations
- **Hibernate** (included in Spring Data JPA) – manages object-table mappings
- **PostgreSQL JDBC** driver – allows Java programs to connect to PostgreSQL database only by using Java code
- **pgAdmin4**– GUI used for managing the database

## REQUIREMENTS

#### Actors:

All these inherit User:
- Student
- Teacher
- Chief of Department (inh teacher) 
- Staff
- Admin


#### Actions:

##### User:
- Sign in
- Manage profile
- Logout

##### Student actions:
- Enroll
- View curriculum
- Manage optional courses
- Sign contract of studies
- View grades

##### Staff:
- Assign student to optional
- View students in group by result
- View student in year by result or criteria
- View students rakings after semester
- Generate document (extende above)

##### Teacher:
- Propose optional courses
- Add grade for student

##### Chief of Department:
- Review list of optional courses
- Set number of student for course
- View teacher with best/worst results
- View disciplines for teacher

##### Admin:
- Create accounts
