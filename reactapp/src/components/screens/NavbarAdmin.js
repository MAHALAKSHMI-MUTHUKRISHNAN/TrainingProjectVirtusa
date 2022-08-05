import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import "../styles/Navbar.css";

function NavbarAdmin() {
  const [click, setClick] = useState(false);

  const handleClick = () => setClick(!click);
  const closeMobileMenu = () => setClick(false);

 

  const handleLogout = () => {
    closeMobileMenu();
    localStorage.clear();
  }


  return (
    <>
      <nav className='navbar'>
        <div className='navbar-container'>
          <Link to='/' className='navbar-logo' onClick={closeMobileMenu}>
            FRAMES.IN
            <i class='fab fa-typo3' />
          </Link>
          <div className='menu-icon' onClick={handleClick}>
            <i className={click ? 'fas fa-times' : 'fas fa-bars'} />
          </div>
          <ul className={click ? 'nav-menu active' : 'nav-menu'}>

          <li className='nav-item'>
              <Link
                to='/admin/home'
                className='nav-links'
                onClick={closeMobileMenu}
              >
                Home
              </Link>
            </li>

            <li className='nav-item'>
              <Link
                to='/admin/bookings'
                className='nav-links'
                onClick={closeMobileMenu}
              >
                Bookings
              </Link>
            </li>
           
              <li className='nav-item'>
              <Link
                to='/admin/dashboard'
                className='nav-links'
                onClick={closeMobileMenu}
              >
               Dashboard
              </Link>
            </li>
            <li className='nav-item'>
              <Link
                to='/'
                className='nav-links'
                onClick={handleLogout}
              >
                Logout
              </Link>
            </li>
            
          </ul>
          
        </div>
      </nav>
    </>
  );
}

export default NavbarAdmin;