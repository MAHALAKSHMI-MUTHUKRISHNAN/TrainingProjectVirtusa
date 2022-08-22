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
import UserProfile from './components/screens/UserProfile';
import AdminRoute from "./AdminRoute";
import UserRoute from "./UserRoute";
import UnAuthorized from "./components/screens/UnAuthorized";
import Error from "./components/screens/Error";
import Contact from './components/screens/Contact';

function App() {
  return (
    <div className="App">
     <Router>
      <Switch>
      <Route path="/" exact component={Home}></Route>
        <Route path="/images" exact component={PhotoGallery}></Route>
       <Route path="/Login" exact component={Login}></Route>
        <Route path="/Register" exact component={Register}></Route>
        <UserRoute path="/user/home" exact component={HomeUser}></UserRoute>
        <AdminRoute path="/admin/home" exact component={HomeAdmin}></AdminRoute>
        <AdminRoute path="/admin/addevent" exact component={AddEvent}></AdminRoute>
        <UserRoute path="/user/booking" exact component={Booking}></UserRoute>
        <UserRoute path="/user/mybookings" exact component={ViewUserBookings}></UserRoute>
        <AdminRoute path="/admin/bookings" exact component={AllBookings}></AdminRoute>
        <AdminRoute path="/admin/addphotos" exact component={AddPhotos}></AdminRoute>
        <AdminRoute path="/admin/dashboard" exact component={AdminProfile}></AdminRoute>
        <UserRoute path="/payment" exact component={CreditCardForm}></UserRoute>
        <Route path="/services" exact component={Services}></Route>
        <Route path="/unauthorized" exact component={UnAuthorized}></Route>
        <Route path="/contact" exact component={Contact}></Route>
        <UserRoute path="/user/profile" exact component={UserProfile}></UserRoute>
        <Route path="/**" exact component={Error}></Route>
        
      </Switch>
    
     </Router>
    </div>
  );
}

export default App;
