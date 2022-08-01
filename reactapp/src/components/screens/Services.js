import React, { useEffect, useState } from "react";
import "../styles/Services.css"
import { motion} from "framer-motion";
import axiosObject from "../../api/bootapi";
import Navbar from "./Navbar";

const imageAnimate={
    offscreen:{x:-100, opacity:0},
    onscreen:{
    x:0,
    opacity:1,
    rotate:[0,10,0],
    transition: {type:"spring",
    bounce:0.4,
    duration:1}
  }

}

const textAnimate={
    offscreen:{y:100, opacity:0},
    onscreen:{y:0,
    opacity:1,
    transition: {type:"spring",
    bounce:0.4,
    duration:1}
 }

}

function Animate({ image, h2, p,id }) {
   
  return (
    <motion.div className="cards" id={id}
        initial={"offscreen"}
        whileInView={"onscreen"}
        viewport={{once:false, amount:0.5}}
        transition={{staggerChildren:0.5}}
    >
      <motion.div className="image-containers"       
        variants={imageAnimate}
      ><img src={image} alt="images of events" style={{height:"150px", width:"2000px"}}></img></motion.div>
      <motion.h2 
        variants={textAnimate}
      >{h2}</motion.h2>
      <motion.p
         variants={textAnimate}     
      >{p}</motion.p>
    </motion.div>
  );
}

export default function Services() {
    const [events,setEvents]=useState([]);


    const getAllEvents=()=>{
        axiosObject.get(`/viewAllEvents`).then(
            (response)=>{
              console.log("Events fetched");
              setEvents(response.data);
            }
          ).catch((error)=>{
            if(error.response){
              
            }
          });
        }
    useEffect(()=>{
        document.title= "Events || Home"
        getAllEvents();
        },[]);
  return (
    <>
    <Navbar/>

     <h3 style={{height:"50px",color:"black",background:"linear-gradient(to bottom, #33ccff 0%, #ff99cc 100%)",textAlign:"center"}}>We give Best services!</h3>
                  
    {events.map((item, index) => (
    
    
      <div className="card-wrappers" key={index}>
          <Animate image={item.imageurl} h2={item.name} p={item.details}  />
      </div>

    ))}; 
 </>
)}