# AcademicMisinformation
### `Team Amice`

<hr>

## Client (web):

### Technology: React
People: Nuti, Andreea, Iulia, Ed

<hr>

## Backend:

### Technology: Java SpringBoot
People: Andreea, Alex, Calin

<hr>

## Database:

### Technology: PostgreSQL
People: Alex, Calin, Andreea

<hr>

## Requirements

### Actors:

All inherit User:
- Student
- Teacher
- Chief of Department (inherits teacher)
- Staff
- Admin

### Actions:

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
- Generate document (extended above)

##### Teacher:
- Propose optional courses
- Add grade for student

##### Chief of Department:
- Review list of optional courses
- Set number of students for course
- View teacher with best/worst results
- View disciplines for teacher

##### Admin:
- Create accounts
