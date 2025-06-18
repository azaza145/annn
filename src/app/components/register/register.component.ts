import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerData = {
    firstName: '',
    lastName: '',
    email: '',
    department: '',
    password: ''
  };
  
  confirmPassword = '';
  acceptTerms = false;

  onSubmit() {
    if (this.registerData.password !== this.confirmPassword) {
      alert('Les mots de passe ne correspondent pas');
      return;
    }
    
    console.log('Données d\'inscription:', this.registerData);
    // Ici, vous ajouterez la logique d'inscription
  }
}

