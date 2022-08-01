import React from 'react';
import '../styles/AddEvent.css';
import NavBarAdmin from './NavbarAdmin';
import axiosObject from '../../api/bootapi';
import { ToastContainer, toast } from 'react-toastify';
  import 'react-toastify/dist/ReactToastify.css';

function AddPhotos(){
   
    const [uploadFile, setUploadFile] = React.useState([]);
 
    const upload = (event) => {
      event.preventDefault();
      
      const dataArray = new FormData();
      dataArray.append("file", uploadFile[0]);
        const config = {
            headers :{'content-type':'multipart/form-data'}
        }
        for(let [key,value] of dataArray.entries()){
            console.log(`${key}:${value}`);
        }
        axiosObject.post(`/upload`,dataArray,config).then(
          (response)=>{
             if(response.status===200){
              toast.success(response.data,{autoClose: 2000});
              setTimeout(() => {  window.location.replace('/admin/addPhotos'); }, 2000);
            }
          }).catch((error)=>{
            if(error.response){
              toast.error(error.response.data.details,{autoClose: 5000});
             setTimeout(() => { window.location.replace('/admin/addPhotos'); }, 5000);
              console.log(error.response.data);
            }
          });
        }

    return(
        <>
        <NavBarAdmin/>
        <div className='temp'>
        <div className="container mt-5 ">
            <div className="row" style={{justifyContent:'space-around'}}>
                <div className="col-md-5 text-center">
               
    <ToastContainer/>

       
        <div className='temp'>
          <h1 className='mt-4'style={{fontWeight:"bold"}} >Add Event</h1>
         
       
   
        <label className="btn btn-default">
              <input type="file" onChange={(e) => setUploadFile(e.target.files)} />
            </label>
            <button
              className="btn btn-success"
             
              onClick={upload}
            >
              Upload
            </button>
        </div>
     
    
                </div>

            </div>
        </div>
        </div>
        </>
    );
}
export default AddPhotos;