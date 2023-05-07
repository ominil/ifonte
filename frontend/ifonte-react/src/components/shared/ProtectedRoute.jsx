import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext.jsx";
import {warningNotification} from "../../services/notification.js";

const ProtectedRoute = ({ permissions, children }) => {

    const { isUserAuthenticated, checkUserPermission } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (!isUserAuthenticated()) {
            navigate('/login');
        }

        else if (!checkUserPermission(permissions)) {
            navigate('/');
            warningNotification('Access Denied', `You don't have permission to view this page`)
        }
        console.log('protected route');

    })

    return isUserAuthenticated() ? children : '';
}

export default ProtectedRoute;