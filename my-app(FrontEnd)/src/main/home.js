import React from 'react';
import { Link } from 'react-router-dom';

function home(){

    const authenticate = async (event) => {
        validateForm;
        event.preventDefault();
      
        try {
            const authToken = localStorage.getItem('accessToken');
            console.log(authToken);
            await axios.get('http://localhost:8081/',{
            headers: {
                Authorization: `Bearer ${authToken}`,
            },
        });
    }catch(error){
        console.error('Error fetching User:', error.message);
    }
};
    return(
        <div onLoad={authenticate}>
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
    <div class="horizontalMenu">
        <ul>
          <li id='profile'>
            <Link to="/profile" class='link'>
              <a><span>Profile</span></a>
            </Link>
          </li>
          <li id='address'>
            <Link to="/address" class='link'>
               <a><span>Address</span></a>
            </Link>
          </li>
        </ul>
      </div>
       </div>
    );
}
export default home;