describe('Registro y login', () => {
  const email = Date.now() + '@email.com'
  const pass = 'aaaaaaA1';
  const name = 'Mr.Nombre'

  it('Registro correcto', () => {
    cy.visit('http://localhost:8080/registro.html')
    cy.get('[name="name"]').type(name)
    cy.get('[name="name"]').should('have.value', name)
    cy.get('[name="email"]').type(email)
    cy.get('[name="email"]').should('have.value', email)
    cy.get('[name="password"]').type(pass)
    cy.get('[name="password"]').should('have.value', pass)
    cy.get('[name="password2"]').type(pass)
    cy.get('[name="password2"]').should('have.value', pass)
    cy.contains('Registrarse').click()
    cy.url().should('include', '/login.html')
    cy.contains('¡Registrado! Prueba a entrar')
  })

  // TODO#13 Hecho
  // Implementa el siguiente test E2E del frontend web para
  // verificar que se realiza el login correctamente con el usuario
  // previamente registrado
  it('Login correcto', () => {
        cy.visit('http://localhost:8080/login.html');
        cy.get('[name="email"]').type(email); // Escribe el email
        cy.get('[name="email"]').should('have.value', email); // Verifica que el email esté en el campo
        cy.get('[name="password"]').type(pass); // Escribe la contraseña
        cy.get('[name="password"]').should('have.value', pass); // Verifica que la contraseña esté en el campo
        cy.contains('Iniciar sesión').click(); // Hace click en el botón de login

            // Verifica que la página redirija a la página de inicio o perfil (por ejemplo)
        cy.url().should('include', '/app.html'); // Nos aseguramos que está en la página de app.

  })
})
