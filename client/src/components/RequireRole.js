import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

export function RequireRole({ allowedRoles, children }) {
  const auth = useSelector((state) => state.auth);

  if (!auth.isLoggedIn) return <Navigate to="/login" replace />;

  if (!allowedRoles.includes(auth.user.role))
    return <Navigate to="/notallowed" replace />;

  return children;
}
