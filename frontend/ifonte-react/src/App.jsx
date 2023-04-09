import {Wrap, WrapItem, Spinner, Text} from '@chakra-ui/react'
import SimpleSidebar from "./components/shared/SideBar.jsx";
import {getEmployers} from "./services/client.js";
import { useEffect, useState } from "react";
import Card from "./components/Card.jsx";
import CreateEmployerDrawer from "./components/CreateEmployerDrawer.jsx";
import {errorNotification} from "./services/notification.js";

const App = () => {

    const [employers, setEmployers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);

    const fetchEmployers = () => {
        setLoading(true);
        getEmployers().then(res => {
            setEmployers(res.data)
        }).catch(err => {
            setError(true)
            errorNotification(
                err.code,
                err.response?.data.message || err.response
            )
        }).finally( () => setLoading(false))
    }

    useEffect(() => {
        fetchEmployers()
    }, [])

    if (loading) {
        return (
            <SimpleSidebar>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SimpleSidebar>
        )
    }

    if (error) {
        return (
            <SimpleSidebar>
                <CreateEmployerDrawer
                    fetchEmployers={fetchEmployers}
                >

                </CreateEmployerDrawer>
                <Text mt={5}>Oooops there was an error</Text>
            </SimpleSidebar>
        )
    }

    if (employers.length <= 0) {
        return (
            <SimpleSidebar>
                <CreateEmployerDrawer
                    fetchEmployers={fetchEmployers}
                >

                </CreateEmployerDrawer>
                <Text mt={5}>No employers Available</Text>
            </SimpleSidebar>
        )
    }

    return(
        <SimpleSidebar>
            <CreateEmployerDrawer
                fetchEmployers={fetchEmployers}
            ></CreateEmployerDrawer>
            <Wrap spacing={'30px'} justify={'center'}>
                {employers.map((employer, index) => (
                    <WrapItem key={index}>
                        <Card
                            {...employer}
                            fetchEmployers={fetchEmployers}
                        ></Card>
                    </WrapItem>
                ))}
            </Wrap>
        </SimpleSidebar>

    )
}

export default App;