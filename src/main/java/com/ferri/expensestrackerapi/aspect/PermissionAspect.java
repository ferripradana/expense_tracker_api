package com.ferri.expensestrackerapi.aspect;


import com.ferri.expensestrackerapi.model.ERole;
import com.ferri.expensestrackerapi.model.Permission;
import com.ferri.expensestrackerapi.model.Role;
import com.ferri.expensestrackerapi.repository.RoleRepository;
import com.ferri.expensestrackerapi.security.utils.SecurityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private RoleRepository roleRepository;

    @Around("execution(@com.ferri.expensestrackerapi.aspect.PermissionCheck * *(..)) && @annotation(permissionCheck)")
    public Object doSomething(ProceedingJoinPoint pjp, PermissionCheck permissionCheck) throws Throwable {
        if (permissionCheck.workspace().length > 0 && SecurityUtil.getUser() != null) {

            List<Permission> permissionList = new ArrayList<>();
            for (GrantedAuthority roleName : SecurityUtil.getUser().getAuthorities()) {
                ERole eRole = ERole.valueOf(roleName.toString());

                Role role = roleRepository.findByName(eRole)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                Collection<Permission> permission = role.getPermissions();
                permission.forEach(permission1 -> permissionList.add(permission1));
            }

            Function<Permission, Boolean> permissionFunction = permission -> {
                if (permissionCheck.read() && permission.getRead()) {
                    return true;
                }
                if (permissionCheck.write() && permission.getWrite()) {
                    return true;
                }
                if (permissionCheck.delete() && permission.getDelete()) {
                    return true;
                }
                return false;
            };

            final boolean[] hasPermission = {false};
            permissionList.forEach(permission -> {
                hasPermission[0] = permissionFunction.apply(permission);
                if (hasPermission[0]){
                    return;
                }
            });

            if(!hasPermission[0]){
                throw new AccessDeniedException("Do not has permission");
            }

        }
        return pjp.proceed();
    }

}
