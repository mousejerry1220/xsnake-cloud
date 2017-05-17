package org.xsnake.miss.api.core.model;

import java.io.Serializable;
import java.util.List;

import org.xsnake.miss.api.core.entity.EntityAttributesDefinition;
import org.xsnake.miss.api.core.entity.EntityDefinition;

public class EntityDefinitionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityDefinition entityDefinition;

	private List<EntityAttributesDefinition> attributes;

	public EntityDefinitionModel(EntityDefinition entityDefinition, List<EntityAttributesDefinition> attributes) {
		this.entityDefinition = entityDefinition;
		this.attributes = attributes;
	}

	public List<EntityAttributesDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<EntityAttributesDefinition> attributes) {
		this.attributes = attributes;
	}

	public EntityDefinition getEntityDefinition() {
		return entityDefinition;
	}

	public void setEntityDefinition(EntityDefinition entityDefinition) {
		this.entityDefinition = entityDefinition;
	}

}
