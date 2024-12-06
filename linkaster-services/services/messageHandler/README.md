# Message Handler Service
Written by: Javier Ortega Mendoza -> [javsort](https://github.com/javsort)
I got to take notes of my own things to keep in check so:

## Overall functionality
1. First, Messaging Service gets pinged FROM logicGateway to establish the web-socket with messaging service directly
2. Second, once messaging service ACK's the connection, it returns the websocket link to logic gateway -> front end
3. Third, the websocket link is used for message sending & retrieval

This way we:
- Keep verification when opening the chat
- Reduce authentication layers to a first instance to websocket connection
- Keep it fast. Given The encryption overhead as well.

## Private Messaging
- Public Key RSA Encryption
- Modality:
    - Retrieve destinataries public key
    - Sign message with dest's public key
    - Dest decodes with private key
    - (Same on backwards)

## Group Messaging
- Hybrid encryption
- Modality:
    - Each module generates an AES one-way encryption
    - All the members of the module retrieve the key encrypted with their public keys
    - Members resolve the AES key with their private keys
    - The decrypted AES key allows to decrypt group message