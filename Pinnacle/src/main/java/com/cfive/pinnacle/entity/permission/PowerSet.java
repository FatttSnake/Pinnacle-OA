package com.cfive.pinnacle.entity.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PowerSet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Operation> operationList;

    private List<Menu> menuList;

    private List<Element> elementList;

    private List<File> fileList;
}
