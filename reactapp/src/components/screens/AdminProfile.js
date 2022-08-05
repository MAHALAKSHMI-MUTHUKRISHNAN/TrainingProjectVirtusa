import React from 'react'
import ReactiveButton from 'reactive-button'
import NavbarAdmin from './NavbarAdmin'
import { Link } from 'react-router-dom';

function AdminProfile() {
  return (
    <>
    <NavbarAdmin/>
    <div className="home-body"style={{display:"flex",flexDirection:"column",alignItems:"center",margin:"20px"}}>
    <Link to="/admin/addphotos"> <ReactiveButton idleText={'Add Photos For Gallery'} color='Violet' animation={true}></ReactiveButton></Link>
      </div>
      <div className="home-body"style={{display:"flex",flexDirection:"column",alignItems:"center",margin:"20px"}}>
    <Link to="/admin/addevent"> <ReactiveButton idleText={'Add Events'} color='Violet' animation={true}></ReactiveButton></Link>
      </div>
    </>
  )
}

export default AdminProfile