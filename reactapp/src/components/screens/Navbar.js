import React, { useState, useEffect } from 'react';

import { Link } from 'react-router-dom';
import "../styles/Navbar.css";

function Navbar() {
  const [click, setClick] = useState(false);

  const handleClick = () => setClick(!click);
  const closeMobileMenu = () => setClick(false);

 

  useEffect(() => {
   
  }, []);

  
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
              <Link to='/' className='nav-links' onClick={closeMobileMenu}>
                Home
              </Link>
            </li>
            <li className='nav-item'>
              <Link
                to='/services'
                className='nav-links'
                onClick={closeMobileMenu}
              >
                Services
              </Link>
            </li>
            <li className='nav-item'>
              <Link
                to='/images'
                className='nav-links'
                onClick={closeMobileMenu}
              >
                Galllery
              </Link>
            </li>

            
            <li className='nav-item-signup'>
              <Link
                to='/register'
                className='nav-links-signup'
                onClick={closeMobileMenu}
              >
                SIGN UP
              </Link>
            </li>
          </ul>
         
        </div>
      </nav>
    </>
  );
}

export default Navbar;