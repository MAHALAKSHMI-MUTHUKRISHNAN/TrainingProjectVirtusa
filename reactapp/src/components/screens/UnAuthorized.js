
import React from 'react'
import '../styles/Error.css'
import uad from'../assets/uad.png'
import { Link } from 'react-router-dom'
import { useEffect } from 'react'

export default function UnAuthorized() {
    useEffect(() => {
        document.title = "WatchService  || Access denied";
      },[]);
      const handleLogout = () => {
        localStorage.clear();
      }
  return (
    <div className='error-body' style={{display:"flex",alignItems:"center",flexDirection:"column"}}>
    <h1 className='mt-3'style={{margin:50}}>UNAUTHORIZED ACCESS!</h1>
    <img src={uad} alt="unauthorized access"style={{height:"30%",margin:40}}/>
    <h1>Your account does not have access to this page!</h1>
    <Link
                exact
                to="/"
                activeClassName="active"
                id="landinglink"
                className="nav-links"
                onClick={handleLogout}
              >
                <button  className="btn btn-dark mt-3" >Please go back & Login again!</button>
              </Link>
    
    </div>
  )
}
