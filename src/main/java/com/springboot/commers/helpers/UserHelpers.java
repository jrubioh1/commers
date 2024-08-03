package com.springboot.commers.helpers;

import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.springboot.commers.entities.Rol;

import com.springboot.commers.repositories.IUsersRepository;
import com.springboot.commers.services.IRolService;

@Component
public class UserHelpers {

   
    //Se inicializan a traves de contructor porque al ser final no se puede inicializar directamente con @Autowired
    //Al ser una clase que se va a usar dentro de otras clases que poseen inyecciones, es mejor usar campos finales para 
    //evitar posibles problemas de sobreinyecciones al pasarse a la otra clase donde sera usada.
    
    private final IUsersRepository repository;

    
    private final IRolService serviceRol;

    //@Autowired //Se puede borrar la anotacion pero se deja para que sea mas explicito que lo parametos viene de inyecciones
    public UserHelpers(IUsersRepository userRepository, IRolService rolService){
        this.repository=userRepository;
        this.serviceRol=rolService;
    }




    @Transactional()
    public List<Rol> listOfRolesDb (List<Rol> roles) {

            List<Rol> rolesDb = new ArrayList<>();

            for (Rol rol : roles) {
                
                    rolesDb.add(serviceRol.findByName(rol.getName()).orElseThrow());
            
            }

            return rolesDb;
    
        
    
       
    }

  



}

