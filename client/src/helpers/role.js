const Role = {
  Admin: "ROLE_ADMINISTRATOR",
  Teacher: "ROLE_TEACHER",
  Staff: "ROLE_STAFF",
  Chief: "ROLE_CHIEF",
  Student: "ROLE_STUDENT",
};

export const readableRole = (role) => {
  switch (role) {
    case Role.Admin:
      return "Administrator";
    case Role.Teacher:
      return "Teacher";
    case Role.Staff:
      return "Staff Member";
    case Role.Chief:
      return "Chief of Department";
    case Role.Student:
      return "Student";
    default:
      return "Invalid role";
  }
};

export default Role;
