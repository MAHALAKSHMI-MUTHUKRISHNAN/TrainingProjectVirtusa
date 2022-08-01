import React, { useEffect, useState } from "react";
import Navbar from './Navbar';
import axiosObject from "../../api/bootapi";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {
  faCircleChevronLeft, 
  faCircleChevronRight, 
  faCircleXmark
} from '@fortawesome/free-solid-svg-icons'

import '../styles/PhotoGallery.css'

const PhotoGallery = () => {


    const [images,setImages]=useState([]);

    const getAllImages=()=>{
        axiosObject.get(`/files`).then(
            (response)=>{
              console.log("Images fetched");
              setImages(response.data);
            },(error)=>{
              console.log(error);
            }
          );
    };

    useEffect(()=>{
        document.title= "Events || Home"
        getAllImages();
        },[]);

  const [slideNumber, setSlideNumber] = useState(0)
  const [openModal, setOpenModal] = useState(false)

  const handleOpenModal = (index) => {
    setSlideNumber(index)
    setOpenModal(true)
  }

  // Close Modal
  const handleCloseModal = () => {
    setOpenModal(false)
  }

  // Previous Image
  const prevSlide = () => {
    slideNumber === 0 
    ? setSlideNumber( images.length -1 ) 
    : setSlideNumber( slideNumber - 1 )
  }

  // Next Image  
  const nextSlide = () => {
    slideNumber + 1 === images.length 
    ? setSlideNumber(0) 
    : setSlideNumber(slideNumber + 1)
  }

  return (
    <div>
<Navbar/>
      {openModal && 
        <div className='sliderWrap'>
          <FontAwesomeIcon icon={faCircleXmark} className='btnClose' onClick={handleCloseModal}  />
          <FontAwesomeIcon icon={faCircleChevronLeft} className='btnPrev' onClick={prevSlide} />
          <FontAwesomeIcon icon={faCircleChevronRight} className='btnNext' onClick={nextSlide} />
          <div className='fullScreenImage'>
            <img src={images[slideNumber].url} alt='' />
          </div>
        </div>
      }

      {/* <br />
      Current slide number:  {slideNumber}
      <br />
      Total Slides: {images.length}
      <br /><br /> */}

      <div className='galleryWrap'>
        {
          images && images.map((slide, index) => {
            return(
              <div 
                className='single' 
                key={index}
                onClick={ () => handleOpenModal(index) }
              >
                <img src={slide.url} alt='' />
              </div>
            )
          })
        }
      </div>

    </div>
  )
}

export default PhotoGallery