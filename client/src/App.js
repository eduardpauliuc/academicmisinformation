import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import Login from "./components/login/Login";
import Home from "./components/Home";
import { ThemeProvider } from "styled-components";
import { RequireRole } from "./components/RequireRole";
import Role from "./helpers/role";
import AdminHome from "./components/admin/AdminHome";
import StudentHome from "./components/student/StudentHome";
import NotAllowed from "./components/NotAllowed";
import TeacherHome from "./components/teacher/TeacherHome";
import ChiefHome from "./components/chief/ChiefHome";
import StaffHome from "./components/staff/StaffHome";

const theme = {
  main: "#41B5F6",
  accent: "#BDF841",
};

function App() {
  return (
    <ThemeProvider theme={theme}>
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={
              <RequireRole allowedRoles={[...Object.values(Role)]}>
                <Home />
              </RequireRole>
            }
          />
          <Route exact path="/login" element={<Login />} />
          <Route
            path="/admin"
            element={
              <RequireRole allowedRoles={[Role.Admin]}>
                <AdminHome />
              </RequireRole>
            }
          />
          <Route
            path="/student"
            element={
              <RequireRole allowedRoles={[Role.Student]}>
                <StudentHome />
              </RequireRole>
            }
          />
          <Route
            path="/teacher"
            element={
              <RequireRole allowedRoles={[Role.Teacher]}>
                <TeacherHome />
              </RequireRole>
            }
          />
          <Route
            path="/chief"
            element={
              <RequireRole allowedRoles={[Role.Chief]}>
                <ChiefHome />
              </RequireRole>
            }
          />
          <Route
            path="/staff"
            element={
              <RequireRole allowedRoles={[Role.Staff]}>
                <StaffHome />
              </RequireRole>
            }
          />
          <Route path="/notallowed" element={<NotAllowed />} />
          <Route
            path="*"
            element={
              <RequireRole allowedRoles={[...Object.values(Role)]}>
                <Home />
              </RequireRole>
            }
          />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
