import React, { useEffect, useState } from "react";
import NavbarUser from './NavbarUser';
import axiosObject from "../../api/bootapi";
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { Container} from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import { Link } from "react-router-dom";

function HomeUser() {

  const throwDetails = (value)=>{
    localStorage.setItem("SelectedEvent",JSON.stringify(value));
  }

    const [events,setEvents]=useState([]);
    const [display,setDisplay] =useState("");

    const getAllEvents=()=>{
        axiosObject.get(`/viewAllEvents`).then(
            (response)=>{
              console.log("Events fetched");
              setEvents(response.data);
            }
          ).catch((error)=>{
            if(error.response){
              
             setDisplay(error.response.data.details);
            }
          });
        }
    useEffect(()=>{
        document.title= "Events || Home"
        getAllEvents();
        },[]);

   
    const [filter,setFilter] = useState('');

    const SearchText = (event) =>{
      setFilter(event.target.value);
    }
    let dataSearch = events.filter(item =>{
       return Object.keys(item).some(key =>
            item[key].toString().toLowerCase().includes(filter.toString().toLowerCase())
    )});

  return (
    <>
      <NavbarUser/>
    <div className="home-body"style={{display:"flex",flexDirection:"column",alignItems:"center"}}>

        <Container className='text-center mt-4' style={{width:"40%"}}>
          <Form.Control  id="searchbar" placeholder="Search" value = {filter} onSubmit={SearchText.bind(this)} />

        </Container>
        <div className="fixed-content">
        
        {events.length !==0 ?
        <Row> 
        {dataSearch.map((event) => {<dataSearch key ={event.id}/>
          return (
            
            <Col style={{ padding: '2rem' }} >
                
              <Card style={{ width: '18rem',borderRadius:20 ,marginRight:5,marginLeft:5}}>
                    <Card.Img variant="top" src={event.imageurl} style={{ width: '10rem', height: '10rem',marginLeft:"20%",marginTop:10,borderRadius:"50%" }} />
                <Card.Body>
                  <Card.Title>{event.name}</Card.Title>
                  <Card.Text>
                  {event.details}
                  </Card.Text>
                </Card.Body>

                <Card.Body style={{alignItems:"center"}}>
                <Link to="/user/booking"><button className="btn btn-success " onClick={()=>{throwDetails(event)}}>Book</button></Link>
               
                </Card.Body>
              </Card>
             
            </Col>    
           )
        })}
        
      </Row>
      :
      <Row>
      
<p>{display}</p>
      </Row>
}
      </div>
      
    </div>
    </>
  )
}

export default HomeUser