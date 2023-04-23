import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Button, FormControl, FormLabel, Input, Stack} from "@chakra-ui/react";
import {saveCustomer} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";

const MyTextInput = ({ label, isRequired, ...props }) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <FormControl isRequired={isRequired}>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert status={"error"}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </FormControl>
    );
};

// And now we can use these
const CreateCustomerForm = ({ onSuccess }) => {

    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    email: '',
                    password: '',
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required'),
                    password: Yup.string()
                        .min(8, 'Must be 4 char or more')
                        .required('Required'),
                })}
                onSubmit={(customer, { setSubmitting }) => {
                    setSubmitting(true);
                    saveCustomer(customer)
                        .then(res => {
                            successNotification(
                                "Customer saved",
                                `${customer.name} was successfully saved`
                            )
                            onSuccess(res.headers['authorization']);
                        }).catch(err => {
                            errorNotification(
                                err.code,
                                err.response?.data.message || err.message
                            )
                        }).finally(() => {
                            setSubmitting(false);
                    })
                }}
            >
                {({isValid, isSubmitting}) => (
                    <Form>
                        <Stack spacing={'24px'}>
                            <MyTextInput
                                label="Name"
                                isRequired={true}
                                name="name"
                                type="text"
                                placeholder="name"
                            />

                            <MyTextInput
                                label="Email Address"
                                isRequired={true}
                                name="email"
                                type="email"
                                placeholder="jane@formik.com"
                            />

                            <MyTextInput
                                label="Password"
                                isRequired={true}
                                name="password"
                                type="password"
                                placeholder="Use a secure password"
                            />

                            <Button isDisabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateCustomerForm;