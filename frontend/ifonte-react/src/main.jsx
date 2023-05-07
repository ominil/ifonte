import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import { ChakraProvider } from '@chakra-ui/react'
import { createStandaloneToast } from '@chakra-ui/toast'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import AuthProvider from "./components/context/AuthContext.jsx";
import Login from "./components/login/Login.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import SimpleSidebar from "./components/shared/SideBar.jsx";
import './index.css'
import SignUp from "./components/login/SignUp.jsx";
import { adminPermission, basicPermission} from "./services/permission.js";
import ResourceNotFound from "./components/shared/ResourceNotFound.jsx";

const { ToastContainer } = createStandaloneToast()

const router = createBrowserRouter([
    {
        path: '/login',
        element: <Login />
    },
    {
        path: '/signup',
        element: <SignUp/>
    },
    {
        path: '/admin',
        element: <ProtectedRoute permissions={adminPermission}><SimpleSidebar><App /></SimpleSidebar></ProtectedRoute>,
        errorElement: <ResourceNotFound />
    }, {
        path: '/',
        element: <ProtectedRoute permissions={basicPermission}><SimpleSidebar><h1> User panel </h1></SimpleSidebar></ProtectedRoute>,
        errorElement: <ResourceNotFound />
    }
])



ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <AuthProvider>
                    <RouterProvider router={router} />
                </AuthProvider>
                <ToastContainer />
            </ChakraProvider>
        </React.StrictMode>,
)
