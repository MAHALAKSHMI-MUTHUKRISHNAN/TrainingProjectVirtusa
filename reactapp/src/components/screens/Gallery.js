import React, { useEffect, useState } from "react";
import Navbar from './Navbar';
import axiosObject from "../../api/bootapi";
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';

function Gallery() {


    const [images,setImages]=useState([]);

    const getAllImages=()=>{
        axiosObject.get(`/files`).then(
            (response)=>{
              console.log("Images fetched");
              setImages(response.data);
            },(error)=>{
              console.log(error);
            }
          );
    };

    useEffect(()=>{
        document.title= "Events || Home"
        getAllImages();
        },[]);

   



  return (
    <>
      <Navbar/>
    <div className="home-body"style={{display:"flex",flexDirection:"column",alignItems:"center",margin:"40px"}}>

       
        <div className="fixed-content" style={{margin:"40px"}}>
        <Row>
        {images.map((event) => {<images key ={event.id}/>
          return (
            
            <Col style={{ padding: '2rem' }} >
                
              <Card style={{ width: '18rem',borderRadius:20 ,marginRight:5,marginLeft:5}}>
                 <Card.Img variant="top" src={event.url} style={{ width: '10rem', height: '10rem',marginLeft:"20%",marginTop:10,borderRadius:"50%" }} />
                <Card.Body>
                  <Card.Title>{event.name}</Card.Title>
                  <Card.Text>
               
                  </Card.Text>
                </Card.Body>

            
              </Card>
             
            </Col>    
           )
        })}
        
      </Row>
   
      </div>
      
    </div>
    </>
  )
}

export default Gallery