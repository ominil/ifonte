import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import CreateCustomerForm from "./CreateCustomerForm.jsx";

const AddIcon = () => "+"

const CreateCustomerDrawer = ({fetchCustomers}) => {

    const { isOpen, onOpen, onClose } = useDisclosure()

    return (
        <>
            <Button
                leftIcon={<AddIcon/>}
                colorScheme={"blue"}
                onClick={onOpen}

            >
                Create Customer
            </Button>
            <Drawer isOpen={isOpen} onClose={onClose} size={"lg"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Create your customer</DrawerHeader>

                    <DrawerBody>
                        <CreateCustomerForm
                            onSuccess={fetchCustomers}
                        />
                    </DrawerBody>

                    <DrawerFooter>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    )
}

export default CreateCustomerDrawer;