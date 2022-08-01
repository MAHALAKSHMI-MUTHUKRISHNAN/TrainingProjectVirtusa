import React from 'react'
import {Carousel} from 'react-bootstrap'
import drone from "../assets/drone.jpg"
import girlphoto from "../assets/girlphoto.jpg"
import iphone from "../assets/iphone.jpg"
import firstphoto from "../assets/firstphoto.jpg"

const HeroSlider = () => {
    return (
        <>
            <Carousel>
                <Carousel.Item interval={1000}>
                    <img style={{height: "80vh"}}
                        className="d-block w-100"
                        src={firstphoto}
                        alt="First slide"
                    />
                    <Carousel.Caption>
                        <h3 style={{color:"black",background:"white"}}>Choose us ! Thank later !</h3>
                       
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item interval={500}>
                    <img style={{height: "80vh"}}
                        className="d-block w-100"
                        src={drone}
                        alt="Second slide"
                    />
                    <Carousel.Caption>
                        <h3 style={{color:"black",background:"white"}}>Drone shots with Vera-level view!</h3>
                       
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <img style={{height: "80vh"}}
                        className="d-block w-100"
                        src={girlphoto}
                        alt="Third slide"
                    />
                    <Carousel.Caption>
                        <h3 style={{color:"black",background:"white"}}>Professional Photographers!</h3>
                      
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <img style={{height: "80vh"}}
                        className="d-block w-100"
                        src={iphone}
                        alt="Third slide"
                    />
                    <Carousel.Caption>
                        <h3 style={{color:"black",background:"white"}}>Retouch, and perfect editing !</h3>
                      
                    </Carousel.Caption>
                </Carousel.Item>
            </Carousel>
        </>
    )
}

export default HeroSlider