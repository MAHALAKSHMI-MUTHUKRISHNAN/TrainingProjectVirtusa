import React from 'react';
import LoginForm from "./LoginForm";
import Navbar  from './Navbar';
import Footer from './Footer';


function Login(){
   
    return(
        <>
            <Navbar/>
        <div className="container mt-5 " style={{marginBottom : "50px",borderBlockColor:"red"}}>
            <div className="row" style={{justifyContent:'space-around'}}>
               
                <div className="col-md-5 text-center" style={{borderRadius:"20px",padding:"67px",borderColor:"red"}}>
                    <LoginForm/>
                </div>
               
            </div>
        </div>
        <Footer/>
        </>
    );
}
export default Login;