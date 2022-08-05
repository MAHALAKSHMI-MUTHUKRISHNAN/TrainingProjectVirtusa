import React,{useState,useEffect} from 'react';
import Card from 'react-bootstrap/Card';
import * as Yup from 'yup';
import axiosObject from '../../api/bootapi';
import '../styles/AddEvent.css';
import NavbarUser from './NavbarUser';
import 'react-toastify/dist/ReactToastify.css';
import { Modal } from "react-bootstrap";
import EditUser from './EditUser';

function UserProfile(){

    const [data,setData]=useState([]);
    const getMyDetails=()=>{
        axiosObject.get(`/mydetails`).then(
            (response)=>{
              console.log("Events fetched");
              setData(response.data);
              
            }
          ).catch((error)=>{
            if(error.response){

            }
          });
        }
        const validate = Yup.object({
            name: Yup.string()
              .max(30, 'Must be 30 characters or less')
              .required('Name is Required'),
           username:Yup.string()
              .required('Image url required'),
            password: Yup.string()
              .required('Price Range is required'),
           mobile: Yup.string()
           .min(10,'Mobile number should be 10')
              .max(10, 'Mobile number should be 10')
              .required('Description is required'),
          })
    useEffect(()=>{
        document.title= "Events || Home"
        getMyDetails();
        },[]);

        const[show,setShow]=useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    const [modalData,setModalData] = useState([
        {
        },
    ]);
    return(
        <>
        <NavbarUser/>
        <div className='temp'>
        <div className="container mt-5 ">
          
            
                <Card border="success" style={{  padding:'40px',  textAlign:'center'}}>
        <Card.Header>Name : {data.name}</Card.Header>
        <Card.Body>
          <Card.Title>Username : {data.username}</Card.Title>
          <Card.Text>
           Contact : <br/>
           {data.mobile}<br/>
           {data.email}
          </Card.Text>
          <Card.Text>
            <button className="btn btn-success " onClick={()=>{handleShow();setModalData(data)}}>Edit Details</button>
          </Card.Text>
        </Card.Body>
      </Card>
      <br />
           
        </div>
        <Modal show={show} onHide={handleClose} >
            <Modal.Header closeButton>
                <Modal.Body>
                    <EditUser editItem={modalData}/>
                </Modal.Body>
            </Modal.Header>

        </Modal>
        </div>
        </>
    );
}
export default UserProfile;