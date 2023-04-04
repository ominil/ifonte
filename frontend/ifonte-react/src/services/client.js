import axios from "axios";

export const getEmployers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/employers`)
    } catch (e) {
        throw e;
    }
}