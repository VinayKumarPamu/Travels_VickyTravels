import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LogIn from './login';
import Registration from './reg';
import Home from './home';
import Profile from '../Profile/profile';
import UpdateProfile from '../Profile/updateProfile';
import Address from '../Profile/Address';
import UpdateAddress from '../Profile/updateAddress';
import BookTicket from'../Travels/bookTicket';
import History from '../Travels/history';
import Ticket from '../Travels/pendingTravel';
import Payments from '../Travels/paymentHistory';

//import Contact from './Contact';

const Pages = () => {
  return (
    <Router>
      <Routes>
        <Route path='/home' element={<Home />}/>
        <Route path="/"  element={<LogIn />} />
        <Route path="/new_reg" element={<Registration />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/address" element={<Address />} />
        <Route path="/updateProfile" element={<UpdateProfile />} />
        <Route path="/updateAddress" element={<UpdateAddress />} />
        <Route path='/bookTicket' element={<BookTicket />}/>
        <Route path='/history' element={<History />}/>
        <Route path='/tickets' element={<Ticket />}/>
        <Route path='/paymentHistory' element={<Payments />}/>
      </Routes>
    </Router>
  );
};

export default Pages;
