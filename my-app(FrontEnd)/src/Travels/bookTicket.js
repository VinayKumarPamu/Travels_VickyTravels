import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import "./bookTravel.css";

function bookTicket(){
    const [source, setSource]=useState('');
    const [destination,setDestination]=useState('');
    const [passengerNum,setPassengerNum]=useState('');
    const [journeyDate,setJourneyDate]=useState('');
    let response=null;

    const fetchProfileData = async (event) => {
        event.preventDefault();
        try {
            const authToken = localStorage.getItem('accessToken');
            console.log(authToken);

            if (!source || !destination || !passengerNum || !journeyDate) {
                window.alert('Please fill in all required fields');
                throw new Error('Please fill in all required fields.');
            }
            response=await axios.post('http://localhost:8081/bookTravel',
            {
                source,
                destination,
                passengerNum ,
                journeyDate,
            },
            {  
            headers: {
                Authorization: `Bearer ${authToken}`,
            },
        });
       
        window.alert("Amount for the Travel is: " + response.data.price);
        handleAlertOk(response.data.price, authToken);
        console.log(response.data.price);
         
      
    } catch (error) {
      // Handle errors, e.g., log them or display a user-friendly message
      console.error('Error fetching profile data:', error.message);
    }
    };
    const handleAlertOk = async (price, authToken) => {
        try {
            const response = await axios.post('http://localhost:8081/ConfirmTicket', {
                source,
                destination,
                passengerNum,
                journeyDate,
                price,
            }, {
                headers: {
                    Authorization: `Bearer ${authToken}`,
                },
            });
            console.log(response.data);
            window.alert(response.data);
            window.location.href='http://localhost:3000/tickets';
        } catch (error) {
            console.error('Error storing data:', error);
        }
    };
    return(
        <div>
             <div id='cssmenu' >
        <ul>
        <li><Link to="/home" class='link' ><span>Home</span></Link></li>
            <li><Link to='/bookTicket'class='link' ><span>PLAN your Journey</span></Link></li>
            <li><Link to='/paymentHistory' ><span>payments</span></Link></li>
            <li><Link to='/tickets'><span>tickets</span></Link></li>
            <li><Link to='/history' class='link'><span>History</span></Link></li>
            <li><Link to="/"class="link" ><span>LogOut</span></Link></li>
        </ul>
    </div>
        <div class="bookTravel">
            <form onSubmit={fetchProfileData} >
                <h2>Plan Your Journey Here...!!!</h2>
        <h3>From...</h3>
        <input htmlFor="source" type="text" id="source" value={source}
        onChange={(e) => setSource(e.target.value)}   required />
       
        <h3>To...</h3>
        
        <input htmlFor="destination" type="text" id="destination" value={destination}
        onChange={(e) => setDestination(e.target.value)}  required />
       
        
        <h3>Number of Passengers</h3>
        
        <input htmlFor="passengerNum" type="text" id="passengerNum" value={passengerNum}
        onChange={(e) => setPassengerNum(e.target.value)}   required />
    
        <h3>Date of Travel</h3>
        
        <input htmlFor="journeyDate" type="date" id="journeyDate" value={journeyDate}
        onChange={(e) => setJourneyDate(e.target.value)} required /><br />

        <input type="submit"  value="Book Journey" class="btn"onClick={fetchProfileData}/>
            </form>
       
    </div>
    </div>
    );
}
export default bookTicket;