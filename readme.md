# focusLock

focusLock is an Android application designed to facilitate distraction-free classrooms by utilizing WiFi Direct communication technology. The application acts as a tool for teachers to lock students' phones during class sessions while automatically recording attendance through Firebase Cloud Database.

## Features

- **WiFi Direct Communication**: Uses WiFi Direct technology to establish communication between the teacher's device and students' devices for phone locking functionality.
- **Distraction-Free Classroom**: Enables teachers to remotely lock students' phones during class sessions to minimize distractions and enhance focus.

- **Attendance Recording**: Automatically records attendance data in real-time using Firebase Cloud Database when students' phones are locked.

## Installation

1. Clone the repository: `git clone https://github.com/your-username/focusLock.git`
2. Open the project in Android Studio.
3. Connect the project to your Firebase account by adding your `google-services.json` file to the app module.
4. Build and run the application on your Android device.

## Usage

1. **Teacher Mode**:

   - Launch the application and switch to "Teacher Mode".
   - Connect to the students' devices via WiFi Direct.
   - Use the application interface to lock/unlock students' phones during class sessions.

2. **Student Mode**:
   - Launch the application and switch to "Student Mode".
   - Connect to the teacher's device via WiFi Direct.
   - Follow instructions provided by the teacher for phone locking/unlocking.

## Firebase Integration

- **Authentication**: Implement Firebase Authentication to ensure secure access for teachers and students.
- **Real-time Database**: Utilize Firebase Realtime Database to store attendance data in real-time.

- **Cloud Messaging**: Implement Firebase Cloud Messaging for communication between teacher and students regarding phone locking instructions.

## Contributing

Contributions to focusLock are welcome! Here's how you can contribute:

- Fork the repository.
- Create your feature branch (`git checkout -b feature/NewFeature`).
- Commit your changes (`git commit -am 'Add some feature'`).
- Push to the branch (`git push origin feature/NewFeature`).
- Create a new Pull Request.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For any inquiries or support, please contact the project maintainers:

- [Dev Joshi](mailto:devjoshi1611@gmail.com)
