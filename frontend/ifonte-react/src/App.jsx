import {Wrap, WrapItem, Spinner, Text} from '@chakra-ui/react'
import SimpleSidebar from "./components/shared/SideBar.jsx";
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
                <CreateCustomerDrawer
                    fetchCustomers={fetchCustomers}
                >

                </CreateCustomerDrawer>
                <Text mt={5}>Oooops there was an error</Text>
            </SimpleSidebar>
        )
    }

    if (customers.length <= 0) {
        return (
            <SimpleSidebar>
                <CreateCustomerDrawer
                    fetchCustomers={fetchCustomers}
                >

                </CreateCustomerDrawer>
                <Text mt={5}>No customers available</Text>
            </SimpleSidebar>
        )
    }

    return(
        <SimpleSidebar>
            {/*<CreateCustomerDrawer*/}
            {/*    fetchCustomers={fetchCustomers}*/}
            {/*></CreateCustomerDrawer>*/}
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
        </SimpleSidebar>

    )
}

export default App;