import axios from "axios";

export const getEmployers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/employers`)
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
            update
        )
    } catch (e) {
        throw e;
    }
}

export const deleteEmployer = async (employerId) => {
    try {
        return axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/employers/${employerId}`,
        )
    } catch (e) {
        throw e;
    }
}
