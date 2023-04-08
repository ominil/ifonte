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
import UpdateEmployerForm from "./UpdateEmployerForm.jsx";

const UpdateEmployerDrawer = ({fetchEmployers, initialValue, employerId}) => {

    const { isOpen, onOpen, onClose } = useDisclosure()

    return (
        <>
            <Button
                colorScheme={"blue"}
                bg={'gray.200'}
                color={'black'}
                rounded={'full'}
                _hover={{
                    transforms: 'translateY(-2px)',
                    boxShadow: 'lg'
                }}
                onClick={onOpen}

            >
                Update
            </Button>
            <Drawer isOpen={isOpen} onClose={onClose} size={"lg"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Update employer</DrawerHeader>

                    <DrawerBody>
                        <UpdateEmployerForm
                            fetchEmployers={fetchEmployers}
                            initialValue={initialValue}
                            employerId={employerId}
                        />
                    </DrawerBody>

                    <DrawerFooter>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    )
}

export default UpdateEmployerDrawer;