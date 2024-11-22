# linkaster_application

This the front-end guide to run the linkaster application

## Getting Started

# You must have installed the flutter SDK 

To run the application you must be in the 'linkaster_application' folder

1st download the dependendies with 'flutter pub get'

And then to run it 'flutter run'

It wil ask you to select the device, and select 2 with chrome or 3 with edge other options ask for installation of other resources

# Dockerize
 --Build the Docker image
docker build -t flutter-web .
or 
docker-compose up --build

### Added by Javsort:
# Commands run inside the container:
As soon as the container builds, you get this message:

Container run by:
`docker-compose up`

Commands:
```
# flutter pub get
Resolving dependencies... 
Downloading packages... 
  characters 1.3.0 (1.3.1 available)
  collection 1.19.0 (1.19.1 available)
  intl 0.19.0 (0.20.0 available)
  matcher 0.12.16+1 (0.12.17 available)
  material_color_utilities 0.11.1 (0.12.0 available)
  meta 1.15.0 (1.16.0 available)
Got dependencies!
6 packages have newer versions incompatible with dependency constraints.
Try `flutter pub outdated` for more information.
# flutter pub upgrade                                   <This one deleted logs for some reason>
# 
# flutter pub outdated
Showing outdated packages.
[*] indicates versions that are not the latest available.

Package Name              Current     Upgradable  Resolvable  Latest   

direct dependencies:     
intl                      *0.19.0     *0.19.0     *0.19.0     0.20.0   

dev_dependencies: all up-to-date.

transitive dependencies: 
characters                *1.3.0      *1.3.0      *1.3.0      1.3.1    
collection                *1.19.0     *1.19.0     *1.19.0     1.19.1   
material_color_utilities  *0.11.1     *0.11.1     *0.11.1     0.12.0   
meta                      *1.15.0     *1.15.0     *1.15.0     1.16.0   

transitive dev_dependencies:
matcher                   *0.12.16+1  *0.12.16+1  *0.12.16+1  0.12.17  
You are already using the newest resolvable versions listed in the 'Resolvable' column.
Newer versions, listed in 'Latest', may not be mutually compatible.
# flutter clean
Deleting build...                                                   33ms
Deleting .dart_tool...                                               8ms
Deleting Generated.xcconfig...                                       3ms
Deleting flutter_export_environment.sh...                            2ms
Deleting ephemeral...                                                9ms
Deleting ephemeral...                                                6ms
Deleting ephemeral...                                                8ms
Deleting .flutter-plugins-dependencies...                            1ms
Deleting .flutter-plugins...                                         1ms
# flutter pub get
Resolving dependencies... 
Downloading packages... 
  characters 1.3.0 (1.3.1 available)
  collection 1.19.0 (1.19.1 available)
  intl 0.19.0 (0.20.0 available)
  matcher 0.12.16+1 (0.12.17 available)
  material_color_utilities 0.11.1 (0.12.0 available)
  meta 1.15.0 (1.16.0 available)
Got dependencies!
6 packages have newer versions incompatible with dependency constraints.
Try `flutter pub outdated` for more information.
```