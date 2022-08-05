
import React from 'react'
import '../styles/Error.css'
import meowImg from'../assets/errorpage.png'
import { Link } from 'react-router-dom'
import { useEffect } from 'react'

export default function Error() {
    useEffect(() => {
        document.title = "WatchService  || 404 page not found";
      },[]);
  return (
    <div className='error-body' style={{display:"flex",alignItems:"center",flexDirection:"column"}}>
    <h1 className='mt-3'>Ooops.....</h1>
    <img src={meowImg} alt="cat"style={{height:"50%",marginTop:20}}/>
    <h1>Page you are looking for doesn't exist</h1>
    <Link id="landinglink" to="/"><button className="btn btn-dark mt-3" >Take me home</button></Link>
    </div>
  )
}
