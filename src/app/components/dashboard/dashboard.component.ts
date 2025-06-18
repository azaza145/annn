import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Event {
  id: number;
  title: string;
  description: string;
  date: Date;
  location: string;
  status: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  totalEvents = 12;
  upcomingEvents = 5;
  myRegistrations = 8;
  totalParticipants = 156;

  recentEvents: Event[] = [
    {
      id: 1,
      title: 'Formation Cybersécurité',
      description: 'Formation sur les bonnes pratiques de cybersécurité',
      date: new Date('2025-07-15'),
      location: 'Salle de conférence A',
      status: 'à venir'
    },
    {
      id: 2,
      title: 'Réunion Direction',
      description: 'Réunion mensuelle de la direction générale',
      date: new Date('2025-06-25'),
      location: 'Bureau Direction',
      status: 'terminé'
    },
    {
      id: 3,
      title: 'Atelier Innovation',
      description: 'Atelier sur les nouvelles technologies bancaires',
      date: new Date('2025-07-20'),
      location: 'Salle Innovation',
      status: 'à venir'
    }
  ];

  ngOnInit() {
    // Ici vous pourrez charger les données depuis un service
  }
}

