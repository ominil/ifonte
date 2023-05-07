import {Wrap, WrapItem, Spinner, Text} from '@chakra-ui/react'
import {getCustomers} from "./services/client.js";
import { useEffect, useState } from "react";
import Card from "./components/customer/Card.jsx";
import CreateCustomerDrawer from "./components/customer/CreateCustomerDrawer.jsx";
import {errorNotification} from "./services/notification.js";

const App = () => {

    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);

    const fetchCustomers = () => {
        setLoading(true);
        getCustomers().then(res => {
            setCustomers(res.data)
        }).catch(err => {
            setError(true)
            errorNotification(
                err.code,
                err.response?.data.message || err.response
            )
        }).finally( () => setLoading(false))
    }

    useEffect(() => {
        fetchCustomers()
    }, [])

    if (loading) {
        return (
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
        )
    }

    if (error) {
        return (
            <>
                <CreateCustomerDrawer
                    fetchCustomers={fetchCustomers}
                >

                </CreateCustomerDrawer>
                <Text mt={5}>Oooops there was an error</Text>
            </>
        )
    }

    if (customers.length <= 0) {
        return (
            <>
                <CreateCustomerDrawer
                    fetchCustomers={fetchCustomers}
                >

                </CreateCustomerDrawer>
                <Text mt={5}>No customers available</Text>
            </>
        )
    }

    return(
            <Wrap spacing={'30px'} justify={'center'}>
                {customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <Card
                            {...customer}
                            fetchCustomers={fetchCustomers}
                        ></Card>
                    </WrapItem>
                ))}
            </Wrap>
    )
}

export default App;