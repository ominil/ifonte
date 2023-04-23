import {
    createContext,
    useContext,
    useEffect,
    useState } from "react";
import { login } from "../../services/client.js";
import jwtDecode from "jwt-decode";

const AuthContext = createContext({});

const AuthProvider = ({ children }) => {

    const [customer, setCustomer] = useState(null);
    const setCustomerFromToken = () => {
        const token = localStorage.getItem('access_token');
        if (token) {
            const {sub: username, scopes: roles} = jwtDecode(token);
            setCustomer({
                name: username,
                roles: roles
            })
        }
    }

    useEffect(() => {
        setCustomerFromToken()
    }, [])



    const performLogin = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            login(usernameAndPassword).then(res => {
                const jwtToken = res.headers['authorization'];
                localStorage.setItem('access_token', jwtToken)
                setCustomerFromToken()
                resolve(res)
            }).catch(err => {
                reject(err)
            })
        })
    }

    const logout = () => {
        localStorage.removeItem('access_token');
        setCustomer(null);
    }

    const isUserAuthenticated = () => {
        const token = localStorage.getItem('access_token');

        if (!token) {
            return false;
        }

        const {exp: expiration} = jwtDecode(token);
        if (Date.now() > expiration * 1000) {
            logout();
        }

        return true;
    }

    return (
        <AuthContext.Provider value={{
            customer,
            performLogin,
            logout,
            isUserAuthenticated,
            setCustomerFromToken
        }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext);

export default AuthProvider;