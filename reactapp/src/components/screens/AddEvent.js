import React from 'react';
import '../styles/AddEvent.css';
import AddEventForm from './AddEventForm';
import AddPhotos from './AddPhotos';
import NavBarAdmin from './NavbarAdmin';
function AddCenter(){

    return(
        <>
        <NavBarAdmin/>
        <div className='temp'>
        <div className="container mt-5 ">
            <div className="row" style={{justifyContent:'space-around'}}>
                <div className="col-md-5 text-center">
                    <AddEventForm/>
                    <AddPhotos/>
                </div>

            </div>
        </div>
        </div>
        </>
    );
}
export default AddCenter;