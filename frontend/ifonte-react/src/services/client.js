import axios from "axios";

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
    }
})

export const getEmployers = async () => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/employers`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getEmployerById = async (employerId) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/employers/${employerId}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}


export const saveEmployer = async (employer) => {
    try {
        return axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/employers`,
            employer
        )
    } catch (e) {
        throw e;
    }
}

export const updateEmployer = async (id, update) => {
    try {
        return axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/employers/${id}`,
            update,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const deleteEmployer = async (employerId) => {
    try {
        return axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/employers/${employerId}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const login = async (usernameAndPassword) => {
    try {
        return axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            usernameAndPassword
        )
    } catch (e) {
        throw e;
    }
}
