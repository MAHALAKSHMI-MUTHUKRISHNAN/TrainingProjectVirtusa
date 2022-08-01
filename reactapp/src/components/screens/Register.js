import React from 'react';
import Footer from './Footer';
import Navbar from './Navbar';
import RegisterForm from './RegisterForm';


function Register(){
   
    return(
        <>
        <Navbar/>
        <div className='temp'>
        <div className="container mt-5 ">
            <div className="row" style={{justifyContent:'space-around'}}>
                
                <div className="col-md-5 text-center">
                    <RegisterForm/>
                </div>

            </div>
        </div>
        </div>
        <Footer/>
        </>
    );
}
export default Register;