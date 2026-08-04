# HomeAssist — Home Maintenance Service Booking System
A java and mySQL based java swing enabled desktop application that connects homeowners with local service workers.

# Description:
HomeAssist is a Java and MySQL-based desktop application that connects homeowners with local service workers — plumbers, electricians, carpenters, and other home maintenance professionals. The system is built to solve a simple, everyday problem: finding a reliable worker nearby and booking their time without the back-and-forth of phone calls or word-of-mouth referrals.
The platform serves two types of users. Homeowners can register, browse available workers filtered by city, and book a service for a specific date and time. Workers can register with their profession, mark themselves on or off duty, and manage the bookings they receive.
#Key features:
Secure login and registration for both users and workers, with hashed and salted passwords
Location-based worker discovery, matching users with workers in the same city
Real-time availability tracking, so workers only appear when they're on duty
Booking system with conflict prevention, ensuring a worker can't be double-booked for the same time slot
Profile management, letting users and workers update their contact details and address
Booking history, so users and workers can view past and upcoming appointments
#Built with:
Java (Swing) for the desktop interface
MySQL for data storage
JDBC for database connectivity
DAO (Data Access Object) pattern for clean separation between the UI and database logic
# Scope:
This is a functional prototype focused on the core booking workflow. Planned extensions include an admin approval system for new worker registrations, a review and rating system, and support for workers to set recurring availability (like weekly off days).
