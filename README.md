# intractiv
Intractiv challenge


swagger: '2.0' 

info:
  version: v2 
  title: User and Password 
basePath: "/" 

tags: 
  - name: Compliance
    description: tools to verify the compliance rules
  - name: CRUD
    description: Create Update Verify for user object

paths: 
  "/api/compliance/password/{password}": 
    get: 
      tags: 
      - Compliance
      summary: Return true if password is compliant or false and reasons if not, 
          Complexity =
          at least 8 characters
          at least one digit
          at least one special character (!,#,$,%,&,@)
          at least one uppercase letter 
      consumes:
      - application/json 
      produces: 
      - application/json 
      parameters: 
      - name: password 
        in: path 
        description: password for analysis
        required: true 
        type: string 
      responses: 
        '200': 
          description: compliance result
          schema: 
            "$ref": "#/definitions/Compliance" 
            

  "/api/user/": 
    post: 
      tags: 
      - CRUD
      summary: Create a new user
      consumes: 
      - application/json
      produces: 
      - application/json
      parameters:
      - name: User
        in: body
        description: login to retrieve
        required: true 
        schema: 
              $ref: '#/definitions/User'
      responses: 
        '201': 
          description: user created
        '409':
          description: user already exists
        '400': 
          description: password is not Compliant 
          schema: 
            "$ref": "#/definitions/Compliance" 
            
  "/api/user/{login}": 
    get: 
      tags: 
      - CRUD
      summary: ...
      produces: 
      - application/json 
      parameters: 
      - name: login
        in: path 
        description: login to retrieve
        required: true 
        type: string 
      responses: 
        '200': 
          description: user found
          schema: 
            "$ref": "#/definitions/User" 
        '404': 
          description: user not found 

  "/api/user/{login}/verify": 
    post: 
      tags: 
      - CRUD
      summary: ...
      consumes: 
      - application/json 
      produces: 
      - application/json 
      parameters: 
      - name: login
        in: path 
        description: login to retrieve
        required: true 
        type: string 
      - name: password
        in: body 
        description: password
        required: true 
        schema: 
              $ref: '#/definitions/Password'
      responses: 
        '200': 
          description: password is ok
        '403': 
          description: invalid password

definitions: 
  Compliance: 
    type: object 
    properties: 
      isValid: 
        type: boolean 
      reason: 
        type: string 
  
  User: 
    type: object
    properties: 
      name: 
        type:  string
      phone:
        type: string
      password:
        type: string
        
  Password: 
    type: string
