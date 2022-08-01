import './App.css';
import {BrowserRouter as Router,Switch,Route } from 'react-router-dom';

import Register from './components/screens/Register'
import Login from './components/screens/Login'
import HomeUser from './components/screens/HomeUser';
import Home from './components/screens/Home'
import HomeAdmin from './components/screens/HomeAdmin';
import AddEvent from './components/screens/AddEvent';
import Booking from './components/screens/Booking';
import ViewUserBookings from './components/screens/ViewUserBookings';
import AllBookings from './components/screens/AllBookings';
import Services from './components/screens/Services';
import AddPhotos from './components/screens/AddPhotos';
import PhotoGallery from './components/screens/PhotoGallery';
import CreditCardForm from './components/screens/CreditCardForm';
import AdminProfile from './components/screens/AdminProfile';

function App() {
  return (
    <div className="App">
     <Router>
      <Switch>
        <Route path="/" exact component={Home}></Route>
        <Route path="/images" exact component={PhotoGallery}></Route>
       <Route path="/Login" exact component={Login}></Route>
        <Route path="/Register" exact component={Register}></Route>
        <Route path="/user/home" exact component={HomeUser}></Route>
        <Route path="/admin/home" exact component={HomeAdmin}></Route>
        <Route path="/admin/addevent" exact component={AddEvent}></Route>
        <Route path="/user/booking" exact component={Booking}></Route>
        <Route path="/user/mybookings" exact component={ViewUserBookings}></Route>
        <Route path="/admin/bookings" exact component={AllBookings}></Route>
        <Route path="/admin/addphotos" exact component={AddPhotos}></Route>
        <Route path="/admin/dashboard" exact component={AdminProfile}></Route>
        <Route path="/payment" exact component={CreditCardForm}></Route>
        <Route path="/services" exact component={Services}></Route>
        
      </Switch>
    
     </Router>
    </div>
  );
}

export default App;
